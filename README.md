# NVAssembly

An interpreter for an assembly-style language written in Java. It is not fully implemented yet and some features may be removed or modified. All instructions and immediates are currently in bytecode format.

| GENERAL PURPOSE | LOGIC | BRANCHING | MEMORY | PRINT |
|-----------------|-------|-----------|--------|-------|
| `0x1A: MOVE`    | `0x1B: AND` | `0x1C: IF EQUAL` | `0x1D: LOAD VALUE` | `0x7F: PRINT` |
| `0x2A: ADD`     | `0x2B: OR` | `0x2C: IF NOT EQUAL` | `0x2D: STORE VALUE` | |
| `0x3A: SUBTRACT`| `0x3B: XOR` | `0x3C: LESS THAN` | | |
| `0x4A: NO-OP`   | `0x4B: SRA` | `0x4C: GREATER THAN` | | |
| `0x5A: MULTIPLY`| `0x5B: SLA` | `0x5C: GREATER THAN EQUAL` | | |
| `0x6A: DIVIDE` | | | | |

| USER INPUT |
|------------|
| `0x1E: INTEGER` |


