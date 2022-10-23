CREATE TABLE "Event" (
	"_id" INT GENERATED ALWAYS AS IDENTITY,
	"Name" varchar,
	"Description" varchar,
	"Additional" varchar[],
	"Judges" varchar[],
	"Type" int)
