Table = cell2table(cell(0,3));
Table.Properties.VariableNames = {'i' 'sqrt_alpha' 'err_ass'};

x_0 = 5;
alpha = 5;

[Table, sqrt_alpha] = newton_sqrt_study(alpha, x_0, 200, 1/1000000, Table);

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

function [Table, sqrt_alpha] = newton_sqrt_study(alpha, x0, imax, tolx, Table)
    sqrt_alpha = (x0 + alpha/x0)/2;
    i = 1;
    row = {i, sqrt_alpha, abs( sqrt(alpha) - sqrt_alpha )};
    Table = [Table; row];
    while(i < imax) && (abs(sqrt_alpha-x0) > tolx)
        x0 = sqrt_alpha;
        i = i+1;
        sqrt_alpha = (x0 + alpha/x0) / 2;
        row = {i, sqrt_alpha, abs( sqrt(alpha) - sqrt_alpha )};
        Table = [Table; row];
    end
end
