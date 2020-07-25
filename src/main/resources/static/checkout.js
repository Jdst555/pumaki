/**script de la pagina de checkout */

/**carga la informacion guardada localmente para mostrar resumen de la compra */
function mostrarSeleccion()
{
  let seleccion = sessionStorage.getItem('seleccion');
  seleccion = JSON.parse(seleccion);
  let items = document.querySelector('.items');


  if(seleccion)
  {
    recopilarSeleccion(seleccion);
    items.innerHTML = '';
    Object.values(seleccion).map((item, i) => {

      if(item.cantidadEnCanasta > 0){
        console.log(item);

        items.innerHTML += `<div class="checkout_producto">
          <div class="prod"><span>${item.nombre}<ion-icon name="trash-outline" class="trash" id="${item.codigo}"></ion-icon></span></div>
          <div class="price">USD ${item.precio}</div>
          <div class="qty">${item.cantidadEnCanasta}</div>
          <div class="total">USD ${Math.round(((item.precio * item.cantidadEnCanasta) + Number.EPSILON) * 100) / 100}</div>

        </div>
        `
      }
    }
  );
    items.innerHTML += `<div class="totalSeleccion">
      <h4 class="tituloTotal">
        Total compra
      </h4>
      <h4 class="totalNumero">
      USD ${sessionStorage.getItem('total')}
      </h4>
    </div>`

    let trash = document.querySelectorAll('.trash');
    if(trash)
    {
      for(let i = 0; i < trash.length; i++)
      {
        trash[i].addEventListener('click', () => {eliminarProducto(trash[i], seleccion)});
      }
    }

  }
}
/**permite eliminar de la seleccion un producto(todos de un mismo producto) */
function eliminarProducto(elemento, seleccion)
{
  let codigo = elemento.id;
  seleccion.forEach((item) => {
    if(item.codigo == codigo)
    {
      item.cantidadEnCanasta = 0;
    }
  });

  let nuevaCantidad = 0;
  let nuevoTotal = 0;
  seleccion.forEach((item) => {
    if(item.cantidadEnCanasta > 0)
    {
      nuevaCantidad += item.cantidadEnCanasta;
      nuevoTotal += (item.cantidadEnCanasta * item.precio);
      nuevoTotal = Math.round(nuevoTotal * 100) / 100;
    }
  }

  );
  sessionStorage.setItem('total', nuevoTotal);
  sessionStorage.setItem('cantidad', nuevaCantidad);
  sessionStorage.setItem('seleccion', JSON.stringify(seleccion));
  location.reload();
}
 /**recupera la informacion local */
function recopilarSeleccion(seleccion)
{
  let elementTot = document.querySelector('.tot');
  tot = sessionStorage.getItem('total');
  elementTot.setAttribute('value', tot);
  let input = document.querySelector('.lista');
  seleccion = JSON.stringify(seleccion);
  input.setAttribute('value', seleccion);

}

function validarForma() {
  var address = document.forms['form']['address'].value;
  var active = document.forms['form']['active'].value;
  var total = document.forms['form']['total'].value;
  var lista = document.forms['form']['lista'].value;
  qtyItems = sessionStorage.getItem('cantidad');
  qtyItems = parseInt(qtyItems);
  if (address == "") {
    alert("La direccion de envío debe ser ingresada");
    return false;
  }
  else if(active == "" || total == "" || lista == "" || qtyItems == 0)
  {
    alert('Parece que no hay productos en su canasta!');
    return false;
  }
  else 
  {

    if(confirm('Confirmar que desea realizar esta compra'))
    {
      sessionStorage.clear();
      return true;
    }
    else{
      return false;
    }
  }

  
}

mostrarSeleccion();
