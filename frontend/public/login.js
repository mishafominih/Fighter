$("#login").on("submit", function (event) {
    event.preventDefault();
    let q = $.ajax({
      url: "api/login",
      method: "post",
      dataType: "json",
      data: $(this).serialize(),
      success: function (data) {
        console.log(data);
        let logResult = q.responseJSON;
        console.log(logResult);
        console.log(data);
        console.log('Я работаю')
        localStorage.setItem("user_id", q['id']);
        if ((logResult = true)) {
          window.location.href = "grid.html";
        }
        else{
          console.log("нет");
        }
      },
      error: console.log('не отправился я')
    });
  });