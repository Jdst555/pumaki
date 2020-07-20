console.log('en checkout');
function mostrarSeleccion()
{
  let seleccion = localStorage.getItem('seleccion');
  seleccion = JSON.parse(seleccion);
  if(seleccion)
  {
    console.log(seleccion);
  }
}
mostrarSeleccion();
