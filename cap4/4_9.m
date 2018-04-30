syms x
runge = 1/(1+x^2);
a = -6;
b = 6;

Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'x' 'lebesgue'};

n = 2;
while n<=40

    xi = linspace(a, b, n); % ascisse equispaziate
    fi = zeros(length(xi), 1);

    for i = 1:length(xi)
        fi(i) = subs(runge, xi(i));
    end

    p = @(x) lagrange(xi, fi, x);

    fplot(runge, [a, b])
    hold on
    fplot(p, [a, b])
    plot(xi, fi, 'r*')
    hold off

    print('-dpng', strcat(num2str(n), '.png'));

    iteration = {n, lebesgue_const(xi, a, b, 0.01)};
    Table = [Table; iteration];

    n = n + 2;
end

% mostra la tabella
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

% calcolo inefficiente e grezzo della norma 0
% ma ai fini della stima puo' andare
function [c] = lebesgue_const(xi, a, b, precision)
    c = -Inf;
    for i = a:precision:b
        if lebesgue_fun(i, xi) > c
            c = lebesgue_fun(i, xi);
        end
    end
end

function [l] = lebesgue_fun(x, xi)
    l = 0;
    for k = 1:length(xi)
        l = l + abs(l_k_n(x, xi, k));
    end
end
