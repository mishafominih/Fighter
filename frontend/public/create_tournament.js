$("#create_tournament").on("submit", function (event) {
  event.preventDefault();
  $.ajax({
    url: "http://51.250.97.3/api/create_tournament",
    method: "post",
    dataType: "json",
    data: {
      name: "Чемпионат города 2",
      description: "Городской турнир",
      user_id: localStorage.getItem("user_id"),
      places: "Ковёр 2",
      categories: [{name : ['40', '50','60']}],
      type: "1",
    },
    success: function (data) {
      localStorage.setItem("tournament_id", "");
      localStorage.setItem("tournament_id", data["key"]);
      console.log(data);
    },
    error: function (xhr, status) {
      console.log(xhr.status);
    },
  });
});

// localStorage.setItem("tournament_id", "");
// let value = document.querySelector("#tournament_id");
// value.addEventListener("input", () => {
//   localStorage.setItem("tournament_id", value.value);
//   console.log(localStorage.getItem("tournament_id"));
// });
