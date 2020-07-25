
/*********script de la pagina shop**********/

/*obtiene los botones de incremento y decremento presentes en la pagina*/
let incrementos = document.querySelectorAll('.incremento');
let decrementos = document.querySelectorAll('.decremento');

/**obtener contador de items (carrito) */
let numero = document.getElementById('num');


/*itemsDesplegados es un array que contiene los objetos Item
correspondiente a cada producto desplegado en la pagina*/
let itemsDesplegados = obtenerItems();

/**la variable total guarda la suma total de la seleccion */
let total;

/*solicitus AJAX. Pide el nombre de este usuario y almacena en sessionStorage*/
  const Http = new XMLHttpRequest();
  const url = '/user';
  Http.open("GET", url);
  Http.send();
  Http.onreadystatechange=(e)=>{sessionStorage.setItem('user', Http.responseText);}

/*actualiza el contador de items*/
function actualizaCantidad()
{
  if(sessionStorage.getItem('cantidad'))
  {
    numero.innerHTML = sessionStorage.getItem('cantidad');
  }
}

/*agrega un listener de evento a los botones de incremento*/
for(let i = 0; i < incrementos.length; i++)
{
  incrementos[i].addEventListener('click', () => {
    aumentarCantidad(itemsDesplegados[i]);
    actualizaCantidad();
  }, true)
}

/**agrega un listener de evento a los botones de decremento */
for(let i = 0; i < decrementos.length; i++)
{
  decrementos[i].addEventListener('click', () => {
    disminuirCantidad(itemsDesplegados[i]);
    actualizaCantidad();
  }, true)

}

/*incrementa el numero de items de un producto. Llama a registrarItems() para aumentar la variable cantidadEnCanasta. 
Llama a sum() para actualizar total.
*/
function aumentarCantidad(item){

  //primero verificar lo que esta almacenado en sessionStorage
  let cantidadItems = sessionStorage.getItem('cantidad');
  cantidadItems = parseInt(cantidadItems);
  if(cantidadItems)
  {
      sessionStorage.setItem('cantidad', cantidadItems + 1);
  } else{
    sessionStorage.setItem('cantidad', 1);
  }
  registrarItems(item);
  itemsDesplegados = JSON.parse(sessionStorage.getItem('seleccion'));
  suma();
}

/**inverso de aumentarCantidad() */
function disminuirCantidad(item)
{
  console.log('disminuirCantidad');
  let cantidadItems = sessionStorage.getItem('cantidad');
  cantidadItems = parseInt(cantidadItems);
  if(eliminarItem(item))
  {
      sessionStorage.setItem('cantidad', cantidadItems - 1);
      itemsDesplegados = JSON.parse(sessionStorage.getItem('seleccion'));
      suma();
  }

}
/*actualiza el total de la cantidad de items*/
function suma()
{
  total = parseInt(sessionStorage.getItem('total'));
  if(!total)
  {
    total = 0;
  }
  total = 0;
  itemsDesplegados.forEach((item, i) => {
  total = total + item.cantidadEnCanasta * item.precio;
  });
  total = Math.round(total * 100) / 100;
  console.log('valor a guardar en total: ', total);
  sessionStorage.setItem('total', total);
  return total;

}
/*Aumenta en uno el numero de este item en la canasta y guarda en sessionStorage*/
function registrarItems(item)
{
  item.cantidadEnCanasta = item.cantidadEnCanasta + 1;
  /*usa stringify para convertir a formato JSON*/
  sessionStorage.setItem('seleccion', JSON.stringify(itemsDesplegados));
}

function eliminarItem(item)
{
  console.log('eliminarItem');
  if(item.cantidadEnCanasta == 0)
  {
    return false;
  }
  else
  {
    item.cantidadEnCanasta = item.cantidadEnCanasta - 1;
    sessionStorage.setItem('seleccion', JSON.stringify(itemsDesplegados));
    return true;
  }
}

/*obtiene la informacion de todos los items desplegados en la pagina, crea
un objeto para cada uno y los aloja en un array*/
function obtenerItems()
{
  let seleccion = sessionStorage.getItem('seleccion');
  if(seleccion)
  {
    console.log('si hay algo en sessionStorage seleccion');
    return JSON.parse(seleccion);
  }
  /*lista de items desplegados (objetos)*/
  let listaItems = new Array();

  /*lista de items desplegados (DOM nodos HTML)*/
  let items = document.querySelectorAll('.prod');
  for(let i = 0; i < items.length; i++)
  {

    /*elementos dentro de cada uno de los items*/
    let esteItem = items[i];
    let descendientes = esteItem.getElementsByTagName('*');

    let precio = null;
    let nombre = null;
    let codigo = null;
    /*buscar precio nombre y codigo entre los elementos*/
    for(let n = 0; n < descendientes.length; n++)
    {
      if(descendientes[n].className == 'precio')
      {
        precio = descendientes[n].textContent;
      }
      else if (descendientes[n].className == 'nombre')
      {
        nombre = descendientes[n].textContent;
      }
      else if (descendientes[n].className == 'codigo')
      {
        codigo = descendientes[n].textContent;
      }

    }

    /*instanciar objeto de item*/
    unItem = new Item(nombre, precio, codigo, 0);

    /*poner en la lista de objetos*/
    listaItems.push(unItem);
  }
  return listaItems;

}


actualizaCantidad();
/*Objeto que representa un item y en la canasta*/
function Item(nombre, precio, codigo, cantidadEnCanasta)
{
  this.nombre = nombre;
  this.precio = precio;
  this.codigo = codigo;
  this.cantidadEnCanasta = cantidadEnCanasta;
}
