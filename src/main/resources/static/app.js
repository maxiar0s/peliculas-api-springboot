const contenedor = document.querySelector('#peliculas');
const template = document.querySelector('#pelicula-template');

async function cargarPeliculas() {
  const respuesta = await fetch('/peliculas');
  const datos = await respuesta.json();
  const peliculas = datos._embedded?.peliculas ?? [];
  contenedor.replaceChildren(...peliculas.map(renderizarPelicula));
}

function renderizarPelicula(pelicula) {
  const nodo = template.content.cloneNode(true);
  const articulo = nodo.querySelector('.card');
  articulo.dataset.id = pelicula.id;
  nodo.querySelector('img').src = pelicula.imagenUrl;
  nodo.querySelector('img').alt = `Poster de ${pelicula.titulo}`;
  nodo.querySelector('.genre').textContent = pelicula.genero;
  nodo.querySelector('h2').textContent = `${pelicula.titulo} (${pelicula.anio})`;
  nodo.querySelector('.meta').textContent = `Direccion: ${pelicula.director}`;
  nodo.querySelector('.synopsis').textContent = pelicula.sinopsis;
  nodo.querySelector('.imdb').href = pelicula.imdbUrl;
  nodo.querySelector('.rt').href = pelicula.rottenTomatoesUrl;
  const lista = nodo.querySelector('.review-list');
  cargarResenas(pelicula.id, lista);
  nodo.querySelector('form').addEventListener('submit', (event) => publicarResena(event, pelicula.id, lista));
  return nodo;
}

async function cargarResenas(peliculaId, lista) {
  const respuesta = await fetch(`/peliculas/${peliculaId}/resenas`);
  const resenas = await respuesta.json();
  lista.replaceChildren(...(resenas.length ? resenas.map(renderizarResena) : [mensajeVacio()]));
}

function renderizarResena(resena) {
  const elemento = document.createElement('article');
  elemento.className = 'review';
  elemento.innerHTML = `<strong>${resena.puntuacion}/5 - ${escapeHtml(resena.autor)}</strong><p>${escapeHtml(resena.comentario)}</p>`;
  return elemento;
}

function mensajeVacio() {
  const elemento = document.createElement('p');
  elemento.className = 'empty';
  elemento.textContent = 'Todavia no hay resenas. Se el primero en opinar.';
  return elemento;
}

async function publicarResena(event, peliculaId, lista) {
  event.preventDefault();
  const form = event.currentTarget;
  const body = Object.fromEntries(new FormData(form));
  body.puntuacion = Number(body.puntuacion);
  const respuesta = await fetch(`/peliculas/${peliculaId}/resenas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  });
  if (respuesta.ok) {
    form.reset();
    await cargarResenas(peliculaId, lista);
  }
}

function escapeHtml(texto) {
  return String(texto).replace(/[&<>'"]/g, (char) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' }[char]));
}

cargarPeliculas().catch(() => {
  contenedor.textContent = 'No se pudieron cargar las peliculas.';
});
