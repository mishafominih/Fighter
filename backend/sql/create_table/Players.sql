CREATE TABLE "Players" (
	"_id" INT GENERATED ALWAYS AS IDENTITY,
	"Name" varchar,
	"Surname" varchar,
	"Patronymic" varchar,
	"Link" varchar,
	"Specifications" jsonb,
	"Additional" jsonb
)
