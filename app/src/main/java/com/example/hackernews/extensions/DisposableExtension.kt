package com.example.hackernews.extensions

import io.reactivex.disposables.Disposable

fun Disposable.disposeIfNotAlready() {
    if(!isDisposed) {
        dispose()
    }
}