procedure prueba is
	v: array(-10..1) of integer;
	i: integer;

function f1(x: ref integer) return integer is
procedure f2 (y: ref integer) is
begin
	y:=y+1;
end;

begin
	f2(x);	
	return x;
end;
begin
	v(1):=5;
	put_line("antes de llamar: ",v(1));
	i:=f1(v(1));
	put_line("despues de llamar: ",v(1));
	put_line("resultado: ",i);
end;

