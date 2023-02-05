package app.myoun.dress.api.entity

import app.myoun.dress.api.command.CommandSender

interface Player : Entity, CommandSender {
    companion object
}