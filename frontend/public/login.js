$("#login").on("submit", function (event) {
    event.preventDefault();
    $.ajax({
      url: "http://51.250.97.3/api/login",
      method: "post",
      dataType: "json",
      data: $(this).serialize(),
      success: function (data) {
        localStorage.setItem("user_id", '');
        localStorage.setItem("user_id", data['id']);
        console.log(localStorage.getItem("user_id"));
        if ((data['result'] = true)) {
          window.location.href = "grid.html";
        }
        else{
          console.log("нет");
        }
      },
      error: function (xhr, status) {
        console.log(xhr.status);
      }
    });
  });