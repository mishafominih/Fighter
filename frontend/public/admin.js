add = document.querySelector(".addPart");
del = document.querySelector(".deletePart");
rez = document.querySelector(".competitionItemInputs");
count = 1;
preRez = rez;
add.addEventListener("click", function () {
  rez.innerHTML += `
  <div class="competitionItemInput">
  <input
  class="inputItem last"
  type="text"
  id="list"
  placeholder="Имя/название"
/>
<input
  type="text"
  id="weight"
  placeholder="Вес"
  class="inputItem last"
/>
<input
  type="text"
  id="sex"
  placeholder="Пол"
  class="inputItem last"
/>
<input
  type="text"
  id="age"
  placeholder="Возраст"
  class="inputItem last"
/>
<input
  type="text"
  id="more"
  placeholder="Доп. сведения"
  class="inputItem last"
/>

</div>`;
});

del.addEventListener("click", function () {
  rez.innerHTML = `
  <div class="competitionItemInput">
  <input
  class="inputItem last"
  type="text"
  id="list"
  placeholder="Имя/название"
/>
<input
  type="text"
  id="weight"
  placeholder="Вес"
  class="inputItem last"
/>
<input
  type="text"
  id="sex"
  placeholder="Пол"
  class="inputItem last"
/>
<input
  type="text"
  id="age"
  placeholder="Возраст"
  class="inputItem last"
/>
<input
  type="text"
  id="more"
  placeholder="Доп. сведения"
  class="inputItem last"
/>

</div>`;
});
