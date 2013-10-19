#!/usr/bin/perl

sub trim{
    my $s = shift;
    $s =~ s/^\s+|\s+$//g;
    return $s;
}

sub findPara{
    open(FILE, 'config.properties');
    my $findStr = $_[0];
    while(<FILE>){
        my $str = trim($_);
        if ($str =~ m/^$findStr/) {
            @fields = split(/=/, $str);
            return trim(@fields[1]);
        }
    }
}

$DB_IP = findPara("DB_IP");
$DB_PORT = findPara("DB_PORT");
$DB_ID = findPara("DB_ID");
$DB_PW = findPara("DB_PW");


sub getUserNum{
    my $res =  `mysql -u $DB_ID -p$DB_PW weibo -e "select count(*) from weibo_user"`;
    my @arr = split("\n", $res);
    my $num = @arr[1] + 0;
    return @arr[1] + 0;
}
$lastNum = -1;
while(1){
    $thisNum = getUserNum();
    print $thisNum;
    if ($thisNum == $lastNum){
        `killall java; java -jar weibo.jar > /dev/null &`;
    }
    $lastNum = $thisNum;
    sleep(30);
}

