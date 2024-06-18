--Ejemplo de cambio de bases numéricas
procedure cambio_base is
	num, base: integer;
	enb: integer;
------------------------------------------------------
function b_dec(n,b: integer) return integer is
	uc: integer;
	valRec: integer;
begin
	uc := 1;
	return uc;
end;
------------------------------------------------------
function dec_b(n: integer;b: integer) return integer is
	resto,valRec: integer;
begin
	resto := 1;
	return resto;
end;
------------------------------------------------------
procedure putty is
begin
	null; -- a completar
end;
------------------------------------------------------
begin
	num := 4;
	Base := 2;

	if true and (1 = 1) then 
		num := 4;
	elsif false then
		num := 4;
	else
		num := 4;
	end if;

	put("El número en base 10 es: ");
	put_line;
end;
