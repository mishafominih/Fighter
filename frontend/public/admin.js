add = document.querySelector(".addPart");
del = document.querySelector(".deletePart");
rez = document.querySelector(".competitionItemInputs");
count = 1;
preRez = rez;
add.addEventListener("click", function () {
  rez.innerHTML += `<div class="competitionItemInput">
    <input
    class="inputItem last"
    type="text"
    id="list"
    placeholder="Имя/название"
  />
  <input
    type="text"
    id="more"
    placeholder="Дополнительные сведения"
    class="inputItem last"
  />
</div>`;
});

del.addEventListener("click", function () {
  rez.innerHTML = `<div class="competitionItemInput">
    <input
    class="inputItem last"
    type="text"
    id="list"
    placeholder="Имя/название"
  />
  <input
    type="text"
    id="more"
    placeholder="Дополнительные сведения"
    class="inputItem last"
  />
</div>`;
});
