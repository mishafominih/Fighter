$("#join").on("submit", function (event) {
    event.preventDefault()
    let q = $.ajax({
      url: "http://51.250.97.3/api/join_to_tournament",
      method: "post",
      dataType: "json",
      data: $(this).serialize(),
      success: function (data) {
        let result = q.responseJSON;
        console.log(data);
        console.log(result);
        console.log('Я работаю')
        if ((result = true)) {
          window.location.href = "grid.html";
        }
        else{
            console.log("нет");
        }
      },
      error: console.log('не отправился я')
    });
  });

  