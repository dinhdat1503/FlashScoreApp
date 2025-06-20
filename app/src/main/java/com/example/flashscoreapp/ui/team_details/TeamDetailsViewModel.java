// app/src/main/java/com/example/flashscoreapp/ui/team_details/TeamDetailsViewModel.java
package com.example.flashscoreapp.ui.team_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.liveData;

import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.PositionHeader; // Import PositionHeader
import com.example.flashscoreapp.data.model.domain.Player; // Import Player
import com.example.flashscoreapp.data.model.domain.SquadListItem; // Import SquadListItem
import com.example.flashscoreapp.data.model.domain.Team;
import com.example.flashscoreapp.data.model.remote.ApiTeamInfo; // Import ApiTeamInfo
import com.example.flashscoreapp.data.repository.MatchRepository;
import com.example.flashscoreapp.data.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap; // Dùng LinkedHashMap để giữ thứ tự các vị trí
import java.util.List;
import java.util.Map;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class TeamDetailsViewModel extends ViewModel {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final MutableLiveData<List<Match>> _teamMatches = new MutableLiveData<>();
    public final LiveData<List<Match>> teamMatches = _teamMatches;

    private final MutableLiveData<Team> _teamDetails = new MutableLiveData<>();
    public final LiveData<Team> teamDetails = _teamDetails;

    private final MutableLiveData<List<SquadListItem>> _squad = new MutableLiveData<>(); // Thay đổi kiểu dữ liệu
    public final LiveData<List<SquadListItem>> squad = _squad; // LiveData mới cho danh sách đội hình đã nhóm

    public TeamDetailsViewModel(final MatchRepository matchRepository, final TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public void loadTeamDetails(final int teamId) {
        BuildersKt.launch(ViewModelKt.getViewModelScope(this), (CoroutineContext) null, (Function2) null, new TeamDetailsViewModel$loadTeamDetails$1(teamId, (Continuation) null));
    }

    public void loadTeamSquad(final int teamId) {
        BuildersKt.launch(ViewModelKt.getViewModelScope(this), (CoroutineContext) null, (Function2) null, new TeamDetailsViewModel$loadTeamSquad$1(teamId, (Continuation) null));
    }

    // Class nội bộ để xử lý coroutine cho việc tải chi tiết đội
    // Giả định bạn đã có các class này.
    // Nếu chưa, hãy thêm chúng vào TeamDetailsViewModel.java
    private class TeamDetailsViewModel$loadTeamDetails$1 extends kotlin.coroutines.jvm.internal.SuspendLambda implements Function2<CoroutineScope, Continuation<? super kotlin.Unit>, Object> {
        int label;
        final int teamId;

        TeamDetailsViewModel$loadTeamDetails$1(int teamId, Continuation<? super TeamDetailsViewModel$loadTeamDetails$1> continuation) {
            super(2, continuation);
            this.teamId = teamId;
        }

        @NonNull
        @Override
        public final Continuation<kotlin.Unit> create(@Nullable Object obj, @NonNull Continuation<?> continuation) {
            return new TeamDetailsViewModel$loadTeamDetails$1(this.teamId, continuation);
        }

        @Nullable
        @Override
        public final Object invoke(@NonNull CoroutineScope coroutineScope, @Nullable Continuation<? super kotlin.Unit> continuation) {
            return ((TeamDetailsViewModel$loadTeamDetails$1) create(coroutineScope, continuation)).invokeSuspend(kotlin.Unit.INSTANCE);
        }

        @Nullable
        @Override
        public final Object invokeSuspend(@NonNull Object result) {
            Object var1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    kotlin.ResultKt.throwOnFailure(result);
                    this.label = 1;
                    Object response = teamRepository.getTeamDetails(this.teamId, (Continuation) this);
                    if (response == var1) {
                        return var1;
                    }
                    ApiTeamInfo apiTeamInfo = (ApiTeamInfo) response;
                    if (apiTeamInfo != null && !apiTeamInfo.getResponse().isEmpty()) {
                        TeamDetailsViewModel.this._teamDetails.postValue(apiTeamInfo.getResponse().get(0).getTeam());
                        TeamDetailsViewModel.this.loadTeamSquad(this.teamId); // Tải đội hình sau khi có chi tiết đội
                    }
                    return kotlin.Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    // Class nội bộ để xử lý coroutine cho việc tải đội hình
    private class TeamDetailsViewModel$loadTeamSquad$1 extends kotlin.coroutines.jvm.internal.SuspendLambda implements Function2<CoroutineScope, Continuation<? super kotlin.Unit>, Object> {
        int label;
        final int teamId;

        TeamDetailsViewModel$loadTeamSquad$1(int teamId, Continuation<? super TeamDetailsViewModel$loadTeamSquad$1> continuation) {
            super(2, continuation);
            this.teamId = teamId;
        }

        @NonNull
        @Override
        public final Continuation<kotlin.Unit> create(@Nullable Object obj, @NonNull Continuation<?> continuation) {
            return new TeamDetailsViewModel$loadTeamSquad$1(this.teamId, continuation);
        }

        @Nullable
        @Override
        public final Object invoke(@NonNull CoroutineScope coroutineScope, @Nullable Continuation<? super kotlin.Unit> continuation) {
            return ((TeamDetailsViewModel$loadTeamSquad$1) create(coroutineScope, continuation)).invokeSuspend(kotlin.Unit.INSTANCE);
        }

        @Nullable
        @Override
        public final Object invokeSuspend(@NonNull Object result) {
            Object var1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    kotlin.ResultKt.throwOnFailure(result);
                    this.label = 1;
                    Object response = teamRepository.getTeamSquad(this.teamId, (Continuation) this); // Gọi API lấy đội hình
                    if (response == var1) {
                        return var1;
                    }
                    ApiTeamInfo apiTeamInfo = (ApiTeamInfo) response; // Giả định response chứa ApiTeamInfo
                    if (apiTeamInfo != null && !apiTeamInfo.getResponse().isEmpty() && apiTeamInfo.getResponse().get(0).getPlayers() != null) {
                        List<Player> players = apiTeamInfo.getResponse().get(0).getPlayers();
                        List<SquadListItem> groupedSquad = groupPlayersByPosition(players);
                        TeamDetailsViewModel.this._squad.postValue(groupedSquad);
                    } else {
                        TeamDetailsViewModel.this._squad.postValue(new ArrayList<>()); // Trả về danh sách rỗng nếu không có dữ liệu
                    }
                    return kotlin.Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    // Phương thức nhóm cầu thủ theo vị trí
    private List<SquadListItem> groupPlayersByPosition(List<Player> players) {
        List<SquadListItem> groupedList = new ArrayList<>();
        Map<String, List<Player>> playersByPosition = new LinkedHashMap<>();

        // Định nghĩa thứ tự ưu tiên cho các vị trí (ví dụ, thủ môn lên trước)
        List<String> positionOrder = new ArrayList<>();
        positionOrder.add("Goalkeeper");
        positionOrder.add("Defender");
        positionOrder.add("Midfielder");
        positionOrder.add("Attacker");
        positionOrder.add("Coach"); // Nếu bạn muốn hiển thị HLV
        positionOrder.add("Unknown"); // Các vị trí khác

        // Khởi tạo các danh sách con rỗng cho mỗi vị trí đã định nghĩa
        for (String pos : positionOrder) {
            playersByPosition.put(pos, new ArrayList<>());
        }

        for (Player player : players) {
            String position = player.getPosition() != null && !player.getPosition().isEmpty() ? player.getPosition() : "Unknown";
            playersByPosition.computeIfAbsent(position, k -> new ArrayList<>()).add(player);
        }

        // Sắp xếp các cầu thủ trong mỗi vị trí theo số áo hoặc tên
        for (Map.Entry<String, List<Player>> entry : playersByPosition.entrySet()) {
            Collections.sort(entry.getValue(), new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    if (p1.getNumber() > 0 && p2.getNumber() > 0) {
                        return Integer.compare(p1.getNumber(), p2.getNumber());
                    }
                    return p1.getName().compareToIgnoreCase(p2.getName());
                }
            });
        }

        // Thêm header và cầu thủ vào danh sách cuối cùng theo thứ tự
        for (String position : positionOrder) {
            List<Player> playersInPosition = playersByPosition.get(position);
            if (playersInPosition != null && !playersInPosition.isEmpty()) {
                groupedList.add(new PositionHeader(position));
                groupedList.addAll(playersInPosition);
            }
        }

        // Thêm bất kỳ vị trí "Unknown" nào chưa được xử lý
        if (playersByPosition.containsKey("Unknown") && !playersByPosition.get("Unknown").isEmpty()) {
            List<Player> unknownPlayers = playersByPosition.get("Unknown");
            // Kiểm tra xem header "Unknown" đã được thêm chưa để tránh trùng lặp
            boolean unknownHeaderExists = false;
            for (SquadListItem item : groupedList) {
                if (item instanceof PositionHeader && ((PositionHeader) item).getPositionName().equals("Unknown")) {
                    unknownHeaderExists = true;
                    break;
                }
            }
            if (!unknownHeaderExists) {
                groupedList.add(new PositionHeader("Unknown"));
            }
            groupedList.addAll(unknownPlayers);
        }


        return groupedList;
    }
}