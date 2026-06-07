package app.revanced.patches.bitpit.launcher

import app.revanced.patcher.*
import app.revanced.patcher.patch.BytecodePatchContext
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val BytecodePatchContext.proCheckerConstructor by gettingFirstMethodDeclaratively {
    name("<init>")
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    parameterTypes("Z", "Z", "Z")
    opcodes(
        Opcode.INVOKE_DIRECT, Opcode.IPUT_BOOLEAN, Opcode.IPUT_BOOLEAN, Opcode.IPUT_BOOLEAN, Opcode.RETURN_VOID
    )
    custom {
        immutableClassDef.run {
            superclass == "Ljava/lang/Object;" && virtualMethods.count() == 3 && directMethods.count() == 1 && fields.count() == 3 && fields.all {
                it.type == "Z" && it.accessFlags and AccessFlags.FINAL.value != 0
            }
        }
    }
}