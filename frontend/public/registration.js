$("#registration").on("submit", function () {
    let q = $.ajax({
      url: "api/registration",
      method: "post",
      dataType: "html",
      data: $(this).serialize(),
      success: function (data) {
        let RegResult = q.responseJSON;
        console.log(RegResult);
        console.log(data);
        console.log(logResult);
        if ((RegResult = true)) {
          window.location.href = "grid.html";
        }
        else{
          console.log("нет");
        }
      },
      error: console.log('не отправился я')
    });
  });

  