async function fetchProducts() {
  const files = [
    "content/products/product-1.json",
    "content/products/product-2.json",
    "content/products/product-3.json"
  ];

  const results = await Promise.all(files.map(f => fetch(f).then(r => r.json())));
  return results;
}

function getSlug() {
  const params = new URLSearchParams(window.location.search);
  return params.get("slug");
}

async function renderList() {
  const products = await fetchProducts();
  const container = document.getElementById("product-list");

  if (!container) return;

  products.forEach(p => {
    const div = document.createElement("div");
    div.className = "card";
    div.innerHTML = `
      <img src="${p.thumbnail}">
      <h3>${p.name}</h3>
      <p>${p.year}</p>
    `;
    div.onclick = () => window.location = `product.html?slug=${p.slug}`;
    container.appendChild(div);
  });
}

async function renderDetail() {
  const slug = getSlug();
  if (!slug) return;

  const products = await fetchProducts();
  const product = products.find(p => p.slug === slug);

  const container = document.getElementById("product-detail");
  if (!container || !product) return;

  container.innerHTML = `
    <h1>${product.name}</h1>
    <img class="hero" src="${product.hero}">
    <p>${product.year}</p>

    <ul>
      ${product.description.map(d => `<li>${d}</li>`).join("")}
    </ul>

    <div class="gallery">
      ${product.gallery.map(img => `<img src="${img}">`).join("")}
    </div>

    <iframe width="560" height="315"
      src="${product.youtube}"
      frameborder="0"
      allowfullscreen>
    </iframe>
  `;
}

renderList();
renderDetail();