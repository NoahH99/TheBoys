package com.noahhendrickson.theboys.extensions

import org.bukkit.permissions.Permissible

enum class Permissions(val node: String) {
    ADMIN("theboys.admin")
}

fun Permissible.hasPermission(perm: Permissions) = hasPermission(perm.node)
