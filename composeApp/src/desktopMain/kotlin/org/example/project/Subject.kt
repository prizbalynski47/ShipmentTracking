interface Subject<T> {
    fun subscribe(observer: T)
    fun unsubscribe(observer: T)
    fun notifyObservers()
}