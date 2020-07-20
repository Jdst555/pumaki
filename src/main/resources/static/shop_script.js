
/*script de la pagina shop*/

/*obtiene los botones de incremento presentes en la pagina*/
let incrementos = document.querySelectorAll('.incremento');
let decrementos = document.querySelectorAll('.decremento');
let numero = document.getElementById('num');
/*itemsDesplegados es un array que contiene los objetos Item
correspondiente a cada producto desplegado en la pagina*/
let itemsDesplegados = obtenerItems();
let total;

/*solicitus AJAX. Pide el nombre de este usuario y almacena en localStorage*/
  const Http = new XMLHttpRequest();
  const url = '/user';
  Http.open("GET", url);
  Http.send();
  Http.onreadystatechange=(e)=>{localStorage.setItem('user', Http.responseText);}

function actualizaCantidad()
{
  if(localStorage.getItem('cantidad'))
  {
    numero.innerHTML = localStorage.getItem('cantidad');
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
for(let i = 0; i < decrementos.length; i++)
{
  decrementos[i].addEventListener('click', () => {
    disminuirCantidad(itemsDesplegados[i]);
    actualizaCantidad();
  }, true)

}
/*incrementa el contador de items seleccionados y almacena en localStorage*/
function aumentarCantidad(item){

  let cantidadItems = localStorage.getItem('cantidad');
  cantidadItems = parseInt(cantidadItems);
  if(cantidadItems)
  {
      localStorage.setItem('cantidad', cantidadItems + 1);
  } else{
    localStorage.setItem('cantidad', 1);
  }
  registrarItems(item);
  itemsDesplegados = JSON.parse(localStorage.getItem('seleccion'));
  suma();
}
function disminuirCantidad(item)
{
  let cantidadItems = localStorage.getItem('cantidad');
  cantidadItems = parseInt(cantidadItems);
  if(cantidadItems == undefined || cantidadItems == 0 || cantidadItems == undefined)
  {
    //no hacer nada
  }
  else
  {
    localStorage.setItem('cantidad', cantidadItems - 1);
    eliminarItem(item);
    itemsDesplegados = JSON.parse(localStorage.getItem('seleccion'));
    suma();
  }
}
function suma()
{
  console.log('running suma');
  total = parseInt(localStorage.getItem('total'));
  if(!total)
  {
    total = 0;
  }
  total = 0;
  itemsDesplegados.forEach((item, i) => {
  total = total + item.cantidadEnCanasta * item.precio;
  });
  console.log('valor a guardar en total: ', total);
  localStorage.setItem('total', total);
  return total;

}
/*Aumenta en uno el numero de este item en la canasta y guarda en localStorage*/
function registrarItems(item)
{
  item.cantidadEnCanasta = item.cantidadEnCanasta + 1;
  /*usa stringify para convertir a formato JSON*/
  localStorage.setItem('seleccion', JSON.stringify(itemsDesplegados));
}
function eliminarItem(item)
{
  if(item.cantidadEnCanasta == 0)
  {
    //no hacer nada
  }
  else
  {
    item.cantidadEnCanasta = item.cantidadEnCanasta - 1;
    localStorage.setItem('seleccion', JSON.stringify(itemsDesplegados));
  }
}

/*obtiene la informacion de todos los items desplegados en la pagina, crea
un objeto para cada uno y los aloja en un array*/
function obtenerItems()
{
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
