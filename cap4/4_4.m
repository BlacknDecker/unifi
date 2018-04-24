% funzione esatta
f = @(x) sin(x);
f1 = @(x) cos(x);
interval = [0, 2*pi];

% polinomi interpolanti, function es 1, 2, 3
xi = [0, pi, 2*pi];
fi = [f(0), f(pi), f(2*pi)];
f1i = [f1(0), f1(pi), f1(2*pi)];

pl = @(x) lagrange(xi, fi, x);
pn = @(x) newton(xi, fi, x);
ph = @(x) hermite(xi, fi, f1i, x);

% plots
subplot(4,1,1)
fplot(f, interval)
grid on
title('f(x) = sin(x)')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

subplot(4,1,2)
fplot(pl, interval)
grid on
title('p(x) Lagrange')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

subplot(4,1,3)
fplot(pn, interval)
grid on
title('p(x) Newton')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

subplot(4,1,4)
fplot(ph, interval)
grid on
title('p(x) Hermite')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};
