------------------------------------------------------------------------
--  factorial.al
------------------------------------------------------------------------
procedure factorial is
--------------------------------------------------------------------------
    n,i: integer;
--------------------------------------------------------------------------
function  fact(m: integer) return integer is
--------------------------------------------------------------------------
begin
    if m = 1 then
        return 1;
    else
        return m * fact(m-1);
    end if;
end;
--------------------------------------------------------------------------
begin
    put_line;
    put("Introduce un número entero entre 1 y 20: ");
    get(i);
    put_line("Calcula k! para k=1..",i,".");
    put_line;
    put_line("Habrá MATH overflow en 13! porque los enteros en la máquina p son de 4 bytes.");
    put_line;

    n := 1;
    while n <= i loop
        put_line (n, "! = ", fact (n));
        n := n + 1;
    end loop;
end;
  