package ar.edu.itba.tpHciMobile.ui.main

class NotasDeClase {
    /*
    val x = 10 // read only
    var y = 10 // se puede modificar el valor
    y = 5
    var w = "Soy un String"
    var z: Int // Defino z pero no inicializo
    z = 30
    println("The ecuation x + 1 value is ${x + 1}")
    */

    /*
    // colecciones de datos:
    //  - List - duplicados con orden de agregados
    var readOnlyShapes = listOf("triangle", "square", "circle")
    var shapes = mutableListOf("triangle", "square")
    shapes.add("circle")
    shapes.remove("square")
    println(readOnlyShapes)
    println(shapes)
    println("Qty: ${shapes.count()}. First item: ${shapes.first()}, second item: ${shapes[1]}")
    println("rectangle" in shapes)
    println("triangle" in shapes)
    //  - Set  - no duplicados y sin orden
    val readOnlyFruits = setOf("apple", "banana")
    var fruits = mutableSetOf("apple", "banana")
    println(fruits)
    //  - Map  - key/value donde la key no puede ser repetida
    val myMap = mapOf(1 to "One", 2 to "Two")
    println(myMap)
    println(myMap[1])
    println(myMap.count())
    println(myMap.keys)
    println(myMap.values)
    */

    /*
    // Condicionales:
    var d: Int
    val check = false
    if(check){
        d = 1
    } else {
        d = 2
    }
    // en Kotlin no existe condicion ? then : else. Hay que hacer el condicional con if y else
    val a = 1
    val b = 2
    println(if (a > b) a else b)
    */

    /*
    // el equivalente al switch en Kotlin es el when, primera que matchea entra
    val obj = "hello"

    when (obj){
        "1" -> println("One")
        "hello" -> println("Greeting")
        else -> println("Unknown")
    }

    val result = when (obj){
        "1" -> "One"
        "hello" -> "Greeting"
        else -> "Unknown"
    }
    println(result)

    val temp = 18
    val description = when {
        temp < 0 -> "< 0"
        temp < 10 -> "cold"
        temp > 10 -> "hot"
    }
    */

    /*
    // Rangos en Kotlin
    val r1 = 1..4 // 1, 2, 3, 4
    val r2 = 1..<4 // 1, 2, 3
    val r3 = 4 downTo 1 // 4, 3, 2, 1
    var r4 = 1..5 step 2 // 1, 3, 5
    var r5 = 'a'..'v' // 'a', 'b', ..., 'v'
    */

    /*
    // Iteradores
    for(number in 1..5)
    	println(number)

    val fruits = listOf("orange", "lemon")
    for (fruit in fruits)
    	println(fruit)

    // también existe el while y el do while como en el resto de lenguajes
    */

    /*
    // Funciones
    fun hello(): String{ // se pone luego del : el valor de retorno
        return "Hello, World!"
    }
    println(hello())

    fun sub(x: Int, y: Int): Int {
        return x - y
    }
    println(sub(5,4))
    // se pueden pasar los argumentos en distinto orden
    println(sub(y = 4, x = 5))

    // se le pueden poner valores por defecto a los parametros
    fun sum(x: Int = 2, y: Int = 3): Int {
        return x + y
    }
    println(sum())

    // se puede omitirel valor de retorno en los siguientes casos
    fun sum2(x: Int, y: Int) = x + y
    println(sum2(4, 8))
    */

    /*
    // Funciones temporales
    // en vez de hacer esto
    fun toUpper(v: String): String {
        return v.uppercase()
    }
    // puedo hacer esto
    val toUpperNew = { v: String -> v.uppercase() }
    println(toUpperNew("bye"))
    */

    /*
    // fold
    val values = listOf(1, 2, 3)
    val result = values.fold(0, { x, item -> x + item })
    println(result)

    // Si tengo mucho codigo en la funcion lambda puedo hacer lo siguiente
    // El ultimo parametro no se coloca adentro, se coloca por fuera dentro de llaves
    val result2 = values.fold(0) {
        x, item -> x + item
    }
    */

    /*
    // Clases
    // Tanto id como email tienen getter, email al ser var tiene setter
    // todo automaticamente, sin hacer nada mas
    class Contact(val id: Int, var email: String = "john@email.com"){
        val category: String = "Work"

        fun printId () {
            println("Contact id = ${id}")
        }
    }

    val contact1 = Contact(1)
    contact1.email = "tom@email.com"
    val contact2 = Contact(2, "jane@email.com")
    println(contact1) // no es amigable el imprimir una clase asi nomas
    contact1.printId()

    // Clases de datos, no tienen comportamiento, solo se almacenan datos
    // la usaremos para guardar datos que vienen de la api
    // tiene un toString implementado ya de por si, implementa compare y me permite clonarla
    data class User(var name: String, val id: Int) {

    }

    val user1 = User("John", 1)
    println(user1)
    val user2 = User("Jane", 2)
    println(user2)
    println("Users are the same? ${user1 == user2}")

    val user3 = user2.copy()
    user3.name = "Tom"
    println(user3)

    val user4 = user1.copy(name = "Mathieu", id = 4)
    println(user4)
    */

    /*
    // No se puede hacer lo siguiente
    var msg: String = "Hello world!"
    msg = null
    println(msg)
    */

    /*
    // para manejar nulos se tiene que poner un ? luego del tipo de dato
    var msg: String? = "Hello world!"
    msg = null
    println(msg?.length)
    msg = "Hello world!"
    println(msg?.length)

    // llamadas en cascada con posibles nulos. Evita chequeos de null. Reduce mucho codigo
    // user?.company?.address?.country

    // se puede poner un ?: para que tome un valor si es que es null. Se llama operador elvis
    msg = null
    println("Message length is ${msg?.length ?: 0}")
    */

    /*
    // En xml el @ significa que esta en resources, para acceder desde kotlin se hace R. y ya estamos en resources
    // en el xml dentro de la carpeta manifests se indican cosas generales del proyecto.
    // reglas de contenido, icono de la app, titulo de la app, etc.
    // todo cambiar android:icon en archivo AndroidManifest.xml dentro de la carpeta manifest a un logo nuestro

    // dentro de app/values/strings/strings.xml se definen las palabras en ingles y español y luego para
    // usarlas uso el name que se le puso. Si no esta traducido toma el default.

    // dentro de AndroidManifest.xml en la equiqueta activity aparecen todas nuestras pantallas
    //
     */
}