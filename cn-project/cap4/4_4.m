% funzione esatta
f = @(x) sin(x);
f1 = @(x) cos(x);
interval = [0, 2*pi];

% polinomi interpolanti, function es 1, 2, 3
xi = [0, pi, 2*pi];
fi = [f(0), f(pi), f(2*pi)];
f1i = [f1(0), f1(pi), f1(2*pi)];

% pl = @(x) lagrange(xi, fi, x); stesso polinomio di newton, cambia solo il metodo di calcolo: sceglo quello computazionalmente piu efficiente
pn = @(x) newton(xi, fi, x);
ph = @(x) hermite(xi, fi, f1i, x);

% plots
subplot(3,1,1)
fplot(f, interval)
grid on
title('f(x) = sin(x)')
xlim([0, 2*pi])
xticks([0 pi/2 pi pi+pi/2 2*pi])
xticklabels({'0','\pi/2','\pi','3/2\pi','2\pi'})
ylim([-1.5, 1.5])

subplot(3,1,2)
fplot(pn, interval)
grid on
title('p(x) Lagrange / Newton')
xlim([0, 2*pi])
xticks([0 pi/2 pi pi+pi/2 2*pi])
xticklabels({'0','\pi/2','\pi','3/2\pi','2\pi'})
ylim([-1.5, 1.5])

subplot(3,1,3)
fplot(ph, interval)
grid on
title('p(x) Hermite')
xlim([0, 2*pi])
xticks([0 pi/2 pi pi+pi/2 2*pi])
xticklabels({'0','\pi/2','\pi','3/2\pi','2\pi'})
ylim([-1.5, 1.5])
