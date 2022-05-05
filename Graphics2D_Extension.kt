package sge

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform

// Temporary transform extensions //////////

fun Graphics2D.withTranslate(x: Int, y: Int, lambda: func)                   = performWith(lambda){ translate( x, y )     }
fun Graphics2D.withTranslate(tx: Double, ty: Double, lambda: func)           = performWith(lambda){ translate( tx, ty )   }
fun Graphics2D.withRotate(theta: Double, lambda: func)                       = performWith(lambda){ rotate( theta )       }
fun Graphics2D.withRotate(theta: Double, x: Double, y: Double, lambda: func) = performWith(lambda){ rotate( theta, x, y ) }
fun Graphics2D.withScale(sx: Double, sy: Double, lambda: func)               = performWith(lambda){ scale( sx, sy )       }
fun Graphics2D.withShear(shx: Double, shy: Double, lambda: func)             = performWith(lambda){ shear( shx, shy )     }
fun Graphics2D.withTransform(Tx: AffineTransform, lambda: func)              = performWith(lambda){ transform( Tx )       }

// Color parameter extensions //////////////
fun Graphics2D.fillRect(x: Int, y: Int, width: Int, height: Int, color: Color) {
    setColor(color)
    fillRect(x, y, width, height)
}


// Helper functions ////////////////////////

private typealias func = () -> Unit

/**
 * Helper function with intended use of performing some transformation on a graphics object,
 * then calling a given lambda, and finally revert the transformation.
 */
private fun Graphics2D.performWith(lambda: func, transformFunc: func) {
    val originalTransform = transform
    transformFunc()
    lambda()
    transform = originalTransform
}

