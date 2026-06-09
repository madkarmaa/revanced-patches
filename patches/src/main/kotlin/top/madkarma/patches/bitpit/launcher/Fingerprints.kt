package top.madkarma.patches.bitpit.launcher

import app.revanced.patcher.accessFlags
import app.revanced.patcher.custom
import app.revanced.patcher.gettingFirstMethodDeclaratively
import app.revanced.patcher.immutableClassDef
import app.revanced.patcher.name
import app.revanced.patcher.opcodes
import app.revanced.patcher.parameterTypes
import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.returnType
import app.revanced.patcher.strings
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val BytecodePatchContext.proCheckerConstructor by gettingFirstMethodDeclaratively {
    name("<init>")
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    parameterTypes("Z", "Z", "Z")
    opcodes(
        Opcode.INVOKE_DIRECT,
        Opcode.IPUT_BOOLEAN,
        Opcode.IPUT_BOOLEAN,
        Opcode.IPUT_BOOLEAN,
        Opcode.RETURN_VOID
    )
    custom {
        immutableClassDef.run {
            superclass == "Ljava/lang/Object;" && virtualMethods.count() == 3 && directMethods.count() == 1 && fields.count() == 3 && fields.all {
                it.type == "Z" && it.accessFlags and AccessFlags.FINAL.value != 0
            }
        }
    }
}

internal val BytecodePatchContext.launcherAppOnCreate by gettingFirstMethodDeclaratively {
    name("onCreate")
    returnType("V")
    strings("onCreateApp")
    custom {
        immutableClassDef.superclass == "Landroid/app/Application;"
    }
}