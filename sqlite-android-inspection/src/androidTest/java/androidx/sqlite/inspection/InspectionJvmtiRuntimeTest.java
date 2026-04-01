package androidx.sqlite.inspection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Build;
import androidx.inspection.ArtToolingImpl;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Runtime check: inspection AAR supplies libart_tooling.so in APK, loads, {@link ArtToolingImpl} initializes. */
@RunWith(AndroidJUnit4.class)
public class InspectionJvmtiRuntimeTest {

    @Test
    public void libArtTooling_so_inBaseApk_forPrimaryAbi() throws Exception {
        String apkPath =
                InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageCodePath();
        String abi = Build.SUPPORTED_ABIS[0];
        String entryName = "lib/" + abi + "/libart_tooling.so";
        try (ZipFile zf = new ZipFile(apkPath)) {
            ZipEntry entry = zf.getEntry(entryName);
            assertTrue(
                    "Missing " + entryName + " in " + apkPath + " (add androidx.inspection:inspection)",
                    entry != null);
        }
    }

    @Test
    public void art_tooling_nativeLibrary_loads() {
        System.loadLibrary("art_tooling");
    }

    @Test
    public void artToolingImpl_instance_initializes() {
        assertNotNull(ArtToolingImpl.instance());
    }
}
