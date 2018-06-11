syms f(x,y) fz(x,y)
f(x,y) = x^2 + y^3 - x*y + (1/432);
fsurf(f, [0.06,0.2]);
hold on
fz(x,y) = 0;
zero = fsurf(fz, [0.06,0.2]);
zero.EdgeColor = 'none';
zero.FaceColor = 'red';
plot(1/12, 1/6, 'o','MarkerSize',10, 'MarkerEdgeColor','white');
colorbar
