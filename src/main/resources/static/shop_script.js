
/*script de la pagina shop*/

/*obtiene los botones de incremento presentes en la pagina*/
let incrementos = document.querySelectorAll('.incremento');

/*itemsDesplegados es un array que contiene los objetos Item
correspondiente a cada item desplegado en la pagina*/
let itemsDesplegados = obtenerItems();


  const Http = new XMLHttpRequest();
  const url = '/user';
  Http.open("GET", url);
  Http.send();
  Http.onreadystatechange=(e)=>{localStorage.setItem('user', Http.responseText);}


/*agrega un listener de evento a los botones de incremento*/
for(let i = 0; i < incrementos.length; i++)
{
  incrementos[i].addEventListener('click', () => {
    cantidad(itemsDesplegados[i]);
  }, true)
}

/*incrementa el contador de items seleccionados y almacena en localStorage*/
function cantidad(item){

  let cantidadItems = localStorage.getItem('cantidad');
  cantidadItems = parseInt(cantidadItems);
  if(cantidadItems)
  {
      localStorage.setItem('cantidad', cantidadItems + 1);
  } else{
    localStorage.setItem('cantidad', 1);
  }

  registrarItems(item);
}

/*Aumenta en uno el numero de este item en la canasta y guarda en localStorage*/
function registrarItems(item)
{
  item.cantidadEnCanasta = item.cantidadEnCanasta + 1;
  /*usa stringify para convertir a formato JSON*/
  localStorage.setItem('seleccion', JSON.stringify(itemsDesplegados));
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



/*Objeto que representa un item y en la canasta*/
function Item(nombre, precio, codigo, cantidadEnCanasta)
{
  this.nombre = nombre;
  this.precio = precio;
  this.codigo = codigo;
  this.cantidadEnCanasta = cantidadEnCanasta;
}
