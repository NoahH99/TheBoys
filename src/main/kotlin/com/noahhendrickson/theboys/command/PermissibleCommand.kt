package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.extensions.Permissions

interface PermissibleCommand {
    val permissions: Permissions
}