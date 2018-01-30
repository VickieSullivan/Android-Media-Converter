package com.github.khangnt.mcp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Khang NT on 1/30/18.
 * Email: khang.neon.1997@gmail.com
 */

open class RxFragment: Fragment() {
    private var disposableOnPaused: CompositeDisposable = CompositeDisposable()
    private var disposableOnDestroyed: CompositeDisposable = CompositeDisposable()
    private var disposableOnViewDestroyed: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // renew composite disposable in case Fragment instance is reused
        if (disposableOnDestroyed.isDisposed) disposableOnDestroyed = CompositeDisposable()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // renew disposable when fragment return from back stack
        if (disposableOnViewDestroyed.isDisposed) disposableOnViewDestroyed = CompositeDisposable()
    }

    override fun onResume() {
        super.onResume()
        // renew disposable when fragment resume
        if (disposableOnPaused.isDisposed) disposableOnPaused = CompositeDisposable()
    }

    protected fun Disposable.disposeOnPaused() = disposableOnPaused.add(this)

    protected fun Disposable.disposeOnDestroyed() = disposableOnDestroyed.add(this)

    protected fun Disposable.disposeOnViewDestroyed() = disposableOnViewDestroyed.add(this)

    override fun onPause() {
        super.onPause()
        disposableOnPaused.dispose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableOnViewDestroyed.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableOnDestroyed.dispose()
    }
}