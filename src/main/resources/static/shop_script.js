

let incrementos = document.querySelectorAll('.incremento');

for(let i = 0; i < incrementos.length; i++)
{
  incrementos[i].addEventListener('click', () => {
    cantidad();
  })
}
function cantidad(){
  let cantidadItems = localStorage.getItem('cantidad');
  cantidadItems = parseInt(cantidadItems);
  if(cantidadItems)
  {
      localStorage.setItem('cantidad', cantidadItems + 1);
  } else{
    localStorage.setItem('cantidad', 1);
  }


}
