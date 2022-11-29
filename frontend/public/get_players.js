$.ajax({
	url: 'api/get_players',
	method: 'post',
	dataType: 'html',
	data: {tournament_id: localStorage.getItem('tournament_id'), user: '0'},
	success: function(data){
		console.log(data);
    console.log('Я работаю')
	},
  error: () =>{
    console.log('не отправился я');
    console.log(localStorage.getItem('tournament_id'));
  }
});

  // let months = [
  //   "Январь",
  //   "Февраль",
  //   "Март",
  //   "Апрель",
  //   "Май",
  //   "Июнь",
  //   "Июль",
  //   "Август",
  //   "Сентябрь",
  //   "Октябрь",
  //   "Ноябрь",
  //   "Декабрь",
  // ];
  // let date = new Date();
  // $(".digit").html(date.getDate());
  // $(".month").html(months[date.getMonth()]);