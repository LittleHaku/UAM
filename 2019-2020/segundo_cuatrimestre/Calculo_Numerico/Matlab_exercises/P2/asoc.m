%test de asociatividad
%(a + b) + c y a + (b + c)

disp('Test de asociatividad')
a = input('Dame un numero a: ');
b = input('Dame un numero b: ');
c = input('Dame un numero c: ');
ans1 = (a + b) + c;
ans2 = a + (b + c);
disp('(a + b) + c) vale');
disp(ans1);
disp('a + (b + c) vale');
disp(ans2);