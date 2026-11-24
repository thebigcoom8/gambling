package gambling;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class Updater {

    private static final String LATEST_URL =
        "https://api.github.com/repos/thebigcoom8/gambling/releases/latest";
    private static final String JAR_NAME = "gambling.jar";

    private static final Pattern TAG =
        Pattern.compile("\"tag_name\"\\s*:\\s*\"([^\"]+)\"");

    private static final Pattern ASSET = Pattern.compile(
        "\"name\"\\s*:\\s*\"gambling-[^\"]*\\.zip\""
        + ".*?\"digest\"\\s*:\\s*\"sha256:([0-9a-fA-F]{64})\""
        + ".*?\"browser_download_url\"\\s*:\\s*\"([^\"]+)\"",
        Pattern.DOTALL);

    public record Release(String version, String zipUrl, String sha256) {}

    private Updater() {}

    private static HttpClient client() {
        return HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    }

    private static final boolean DEBUG = System.getenv("GAMBLING_DEBUG") != null;

    private static void dbg(String msg) {
        if (DEBUG) {
            System.out.println("[updater] " + msg);
        }
    }

    public static Release findUpdate() {
        try {
            String current = currentVersion();
            dbg("running version: " + current);
            if (current == null || current.equals("dev")) {
                dbg("skipping: dev build or no version.txt in jar (build with ./build.sh 0.1.0)");
                return null;
            }
            if (runningJar() == null) {
                dbg("skipping: not running from a .jar file");
                return null;
            }
            HttpRequest req = HttpRequest.newBuilder(URI.create(LATEST_URL))
                .header("User-Agent", "gambling-updater")
                .header("Accept", "application/vnd.github+json")
                .timeout(Duration.ofSeconds(5))
                .build();
            HttpResponse<String> resp = client().send(req, HttpResponse.BodyHandlers.ofString());
            dbg("GitHub API status: " + resp.statusCode());
            if (resp.statusCode() != 200) {
                return null;
            }
            Release latest = parseRelease(resp.body());
            if (latest == null) {
                dbg("no usable release: need a tag_name plus a gambling-*.zip asset with a sha256 digest");
                return null;
            }
            dbg("latest release: " + latest.version());
            boolean newer = isNewer(latest.version(), current);
            dbg(newer ? "update available" : "not newer than " + current);
            return newer ? latest : null;
        } catch (Exception e) {
            dbg("failed: " + e);
            return null;
        }
    }

    public static void install(Release r) throws IOException, InterruptedException {
        Path jar;
        try {
            jar = runningJar();
        } catch (Exception e) {
            throw new IOException("could not locate the running jar", e);
        }
        if (jar == null) {
            throw new IOException("not running from a jar; nothing to update");
        }
        Path staged = jar.resolveSibling(JAR_NAME + ".new");
        Path tmpZip = Files.createTempFile("gambling-update", ".zip");
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(r.zipUrl()))
                .header("User-Agent", "gambling-updater")
                .timeout(Duration.ofMinutes(2))
                .build();
            HttpResponse<Path> resp = client().send(req, HttpResponse.BodyHandlers.ofFile(tmpZip));
            if (resp.statusCode() != 200) {
                throw new IOException("download failed: HTTP " + resp.statusCode());
            }

            String actual = sha256(tmpZip);
            if (!actual.equalsIgnoreCase(r.sha256())) {
                throw new IOException("checksum mismatch - refusing to install");
            }

            extractJar(tmpZip, staged);
        } finally {
            Files.deleteIfExists(tmpZip);
        }
    }

    static String currentVersion() throws IOException {
        try (InputStream in = Updater.class.getResourceAsStream("/version.txt")) {
            return in == null ? null : new String(in.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
    }

    static Path runningJar() throws Exception {
        Path p = Path.of(Updater.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        return (Files.isRegularFile(p) && p.toString().endsWith(".jar")) ? p : null;
    }

    static Release parseRelease(String json) {
        Matcher tag = TAG.matcher(json);
        Matcher asset = ASSET.matcher(json);
        if (tag.find() && asset.find()) {
            return new Release(tag.group(1), asset.group(2), asset.group(1));
        }
        return null;
    }

    static boolean isNewer(String latest, String current) {
        int[] a = parts(latest);
        int[] b = parts(current);
        for (int i = 0; i < Math.max(a.length, b.length); i++) {
            int x = i < a.length ? a[i] : 0;
            int y = i < b.length ? b[i] : 0;
            if (x != y) {
                return x > y;
            }
        }
        return false;
    }

    private static int[] parts(String version) {
        String v = version.trim().replaceFirst("^[vV]", "");
        String[] pieces = v.split("\\.");
        int[] out = new int[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            Matcher m = Pattern.compile("^\\d+").matcher(pieces[i]);
            out[i] = m.find() ? Integer.parseInt(m.group()) : 0;
        }
        return out;
    }

    static String sha256(Path file) throws IOException {
        try (InputStream in = Files.newInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) {
                md.update(buf, 0, n);
            }
            return HexFormat.of().formatHex(md.digest());
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
    }

    static void extractJar(Path zip, Path target) throws IOException {
        try (ZipInputStream zin = new ZipInputStream(Files.newInputStream(zip))) {
            ZipEntry e;
            while ((e = zin.getNextEntry()) != null) {
                String name = e.getName();
                if (!e.isDirectory() && (name.equals(JAR_NAME) || name.endsWith("/" + JAR_NAME))) {
                    Files.copy(zin, target, StandardCopyOption.REPLACE_EXISTING);
                    return;
                }
            }
        }
        throw new IOException(JAR_NAME + " not found in the downloaded zip");
    }
}
