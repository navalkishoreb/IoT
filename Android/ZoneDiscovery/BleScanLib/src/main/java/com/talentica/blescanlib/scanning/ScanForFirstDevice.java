package com.talentica.blescanlib.scanning;

import com.talentica.blescanlib.scanning.blemodels.BLEScanResult;
import com.talentica.blescanlib.scanning.service.ScanningView;

/**
 * Created by NavalB on 30-06-2016.
 */

interface ScanForFirstDevice extends ScanningView {
    void launch(BLEScanResult bleScanResult);
}
