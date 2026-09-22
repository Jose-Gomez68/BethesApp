package com.iglesiabethesda.bethesdapp.members.domain.model

data class AttendanceItem(
    val memberUid: String,
    val isPresent: Boolean = false,
    val isLate: Boolean = false
)
/*
* | isPresent | isLate  | Interpretación                |
| --------- | ------- | ----------------------------- |
| `true`    | `false` | ✅ Presente                    |
| `true`    | `true`  | 🕐 Presente, pero llegó tarde |
| `false`   | `false` | ❌ Ausente                     |
| `false`   | `true`  | ❌ **No debería existir**      |
*/
