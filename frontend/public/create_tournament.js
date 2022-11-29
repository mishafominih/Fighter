$("#create_tournament").on("submit", function () {
  let q = $.ajax({
    url: "/api/create_tournament",
    method: "post",
    dataType: "html",
    data: $(this).serialize(),
    success: function (data) {
      console.log(data);
      let logResult = q.responseJSON;
      console.log(logResult);
      console.log(data);
    },
    error: console.log('не отправился я')
  });
});