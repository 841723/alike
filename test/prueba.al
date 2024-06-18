--Ejemplo de cambio de bases numéricas
procedure prueba is
	i, var: integer;
	c, cvar: character;
	b1, b2, b3: boolean;
	v,v2: array(1..10) of integer;


------------------------------------------------------
function test(n: integer; q: ref integer) return integer is 
------------------------------------------------------
	inte: integer;
	cha: character;
	boo, boo1: boolean;
begin

	inte := 0;
	cha := 'a';
	boo := true;
	--boo1 := not boo;
	if true then
		null;
	else
		return 5;
	end if;
	
	return 0;
end;

------------------------------------------------------
function test2 return integer is
------------------------------------------------------
begin
	--put_line("hola");
	return 4;
end;
------------------------------------------------------

------------------------------------------------------
procedure algo1(ival: integer; iref : ref integer; cval: character; cref: ref character; bval: boolean; bref: ref boolean; vval: array(1..10) of integer ; vref: ref array(1..10) of integer) is
------------------------------------------------------
kkk : integer;
begin
	iref := 5;
	vref(5) := 5;
end;
------------------------------------------------------

------------------------------------------------------
function algo2(ival: integer; iref : ref integer; cval: character; cref: ref character; bval: boolean; bref: ref boolean; vval: array(1..10) of integer; vref: ref array(1..10) of integer) return integer is
------------------------------------------------------
begin
	return 56;
end;
------------------------------------------------------

begin
	v(5) := 2;
	--v := v2;
	i := 96;
	c := 'c';
	var := 60;
	b1 := false;

	if true then
		null;
	else
		null;
	end if;
	--get(i, c);
	put_line("hola", test2, i, c, v(5));

	c := int2char(2 + 3);

	put_line("aqui",int2char(97), "kk", char2int('b'));

	i := char2int('d');
	put_line(i);

	i:=test(54, i);
	v(5) := 2;
	v2(5) := 2;
	algo1(i, v(5), c, cvar, b1, b2, v, v2);
	put_line(v(5), "--", v2(5));
	put_line(algo2(i, var, c, cvar, b1, b2, v, v2));

	while i < 100 loop
		i := i + 1;
	end loop;
end;
