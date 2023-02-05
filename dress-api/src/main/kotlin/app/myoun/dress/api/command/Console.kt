package app.myoun.dress.api.command

import app.myoun.dress.api.loader.DressLoader

interface Console : CommandSender {
    companion object : CommandSender by DressLoader.loadImplement(Console::class.java)
}