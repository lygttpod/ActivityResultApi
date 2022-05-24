package com.lygttpod.android.activity.result.api.config

import android.net.Uri
import java.io.File

class CropConfig(uri: Uri, outputUri: Uri) {
    var sourceUri: Uri? = uri
    var outputUri: Uri? = outputUri
    var outputFile: File? = null

    var aspectX: Int = 1
    var aspectY: Int = 1

    var outputX: Int = 200
    var outputY: Int = 200

    fun withUri(uri: Uri): CropConfig {
        this.sourceUri = uri
        return this
    }

    fun withOutputUri(uri: Uri): CropConfig {
        this.outputUri = uri
        return this
    }

    fun withOutputFile(outputFile: File): CropConfig {
        this.outputFile = outputFile
        return this
    }

    fun withAspectX(aspectX: Int): CropConfig {
        this.aspectX = aspectX
        return this
    }

    fun withAspectY(aspectY: Int): CropConfig {
        this.aspectY = aspectY
        return this
    }

    fun withOutputX(outputX: Int): CropConfig {
        this.outputX = outputX
        return this
    }

    fun withOutputY(outputY: Int): CropConfig {
        this.outputY = outputY
        return this
    }
}