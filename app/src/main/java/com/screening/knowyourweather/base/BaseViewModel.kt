package com.screening.knowyourweather.weather

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.*

abstract class BaseViewModel<ViewState>(app: Application): AndroidViewModel(app),
    LifecycleEventObserver {
    private var viewState: ViewState? = null
    private var viewLifecycle: Lifecycle? = null


    fun attachView(viewState: ViewState, viewLifecycle: Lifecycle) {
        this.viewState = viewState
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }

    protected fun viewState(): ViewState? {
        return viewState
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if(event == Lifecycle.Event.ON_DESTROY){
            viewState = null
            viewLifecycle = null
        }
    }


    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }

}