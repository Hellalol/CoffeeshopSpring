$(document).ready(function () {
    const productbox = document.getElementById("productbox");
    $.getJSON("MOCK_DATA.json", function (response) {
        response.forEach(element => {
        productbox.innerHTML += `<div class="product">
      <div id="productinfo">
      <div class="productname">${element.productName}</div>
      <div class="description">${element.description}</div>
      <div class="priceinfo">Pris <div class="price">${element.basePrice}</div> kr</div>
      <button class="minusbtn">-</button>
      <div class="amount">0</div>
      <button class="plusbtn">+</button></div>`
        });
    })
})
