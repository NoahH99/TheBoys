package com.noahhendrickson.theboys.command

import com.noahhendrickson.theboys.Transport

interface TransportationCommand : PlayerCommand {

    val transportationCountdownTasks: MutableList<Transport>
        get() = plugin.transportationCountdownTasks

}
