package king_game_engine.communication

import java.util.*

/**
 * Exists to allow objects to communicate without exposing functions and fields that could be private.
 */
class Signal<T : Function<Unit>> {
    private val connectedReceiverLambdas = LinkedList<T>()

    /**
     * Connect a function to the signal.
     * Will be called when Signal.emit() is called.
     * @param lambda The function to connect.
     */
    fun connect(lambda: T) {
        connectedReceiverLambdas.add(lambda)
    }

    /**
     *  Call all connected functions.
     *  @param lambda A function that takes a connected function as an argument.
     *  Call emit like this:
     *      Signal.emit { it(put, your, arguments, here) }
     */
    fun emit(lambda : (connectedReceiverLambda: T) -> Unit) {
        for (l in connectedReceiverLambdas) {
            lambda(l)
        }
    }
}


private fun main() { // Example to show how the signal works.
    // Imagine playerWasHit being a text member on a global Object acting as a signal bus.
    val playerWasHit = Signal<(enemy_name: String, damage_dealt: Int) -> Unit>()

    // Imagine the different Signal.connect calls coming from different objects.
    playerWasHit.connect { enemy_name, damage_dealt ->
        println("What?! The player was hit by $enemy_name for $damage_dealt damage ?!")
    }

    // Each object can have their own response to the signal being emitted.
    playerWasHit.connect { enemy_name, _ ->
        println("Lmao player. I would beat the living shit out of $enemy_name.")
    }

    // When the signal is emitted like this, all the lambdas will be called with the given arguments.
    playerWasHit.emit { it("Jonathan", 20) }

}
