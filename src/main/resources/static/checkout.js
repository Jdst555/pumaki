
function mostrarSeleccion()
{
  let seleccion = localStorage.getItem('seleccion');
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
          <div class="prod"><span>${item.nombre}</span></div>
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
      USD ${localStorage.getItem('total')}
      </h4>
    </div>`

  }
}
function recopilarSeleccion(seleccion)
{
  let cadenaJSON = '{';
  let input = document.querySelector('.seleccion');
  let elementTot = document.querySelector('.tot');
  seleccion.forEach((item, i) => {
    if(item.cantidadEnCanasta > 0)
    {
      nombre = item.nombre;
      cantidad = item.cantidadEnCanasta;
      cadenaJSON += (nombre + ' : ' + cantidad + ', ');
    }
  }
)

  cadenaJSON += '}';
  input.setAttribute('value', cadenaJSON);
  tot = localStorage.getItem('total');
  elementTot.setAttribute('value', tot);

}

mostrarSeleccion();
