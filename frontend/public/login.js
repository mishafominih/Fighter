$("#login").on("submit", function () {
    let q = $.ajax({
      url: "/api/login",
      method: "post",
      dataType: "html",
      data: $(this).serialize(),
      success: function (data) {
        console.log(data);
        let logResult = q.responseJSON;
        console.log(logResult);
        console.log(data);
        localStorage.setItem("id", q['id']);
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