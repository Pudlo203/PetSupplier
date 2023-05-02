--procedure
create procedure selectPets()
    language sql
    as $$
select * from pets;
$$;
call selectPets()

